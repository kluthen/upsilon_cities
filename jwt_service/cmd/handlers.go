package main

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"
	"time"

	"github.com/google/uuid"
	"github.com/redis/go-redis/v9"
)

var (
	ctx = context.Background()
	rdb *redis.Client
)

// key prefix pour Redis
const keyPrefix = "jwt"
const nbKeys = 10

func serverErrorReply(w http.ResponseWriter, errMsg string) {
	w.WriteHeader(http.StatusInternalServerError)
	w.Write([]byte(errMsg))
}

func authenticationErrorReply(w http.ResponseWriter, errMsg string) {
	w.WriteHeader(http.StatusForbidden)
	w.Write([]byte(errMsg))
}

func invalidRequestErrorReply(w http.ResponseWriter, errMsg string) {
	w.WriteHeader(http.StatusBadRequest)
	w.Write([]byte(errMsg))
}

func getIterationForUser(userId int) (bool, int) {
	iteration, err := rdb.Get(ctx, fmt.Sprintf("%s:%d:iteration", keyPrefix, userId)).Result()
	if err != nil {
		return false, 0 // no value for that key.
	}
	i, err := strconv.Atoi(iteration)
	if err != nil {
		return false, 0 // error in convertion
	}
	return true, i
}

func setKey(userId int, iteration int) (ok bool, key string) {
	key = uuid.New().String()
	err := rdb.Set(ctx, fmt.Sprintf("%s:keys:%s", keyPrefix, key), fmt.Sprintf("%d:%d", userId, iteration), time.Hour).Err() // expire en 24h
	if err != nil {
		return false, ""
	}
	return true, key
}

func setKeys(userId int, iteration int) (bool, [nbKeys]string) {
	var res [nbKeys]string

	for i := range nbKeys {
		ok, nkey := setKey(userId, iteration)
		if !ok {
			return false, [nbKeys]string{"Fail"}
		}

		res[i] = nkey
	}

	return true, res
}

func setIteration(userId int, iteration int) error {
	err := rdb.Set(ctx, fmt.Sprintf("%s:%d:iteration", keyPrefix, userId), iteration, time.Hour).Err()
	if err != nil {
		// failure to set iteration value for user.
		return fmt.Errorf("failed to set iteration")
	}
	return nil
}

// generateKeysForUser will re/generate keys for a given user id.
func generateKeysForUser(w http.ResponseWriter, r *http.Request) {
	// Input Validation

	userID := r.PathValue("user_id")
	userId, err := strconv.Atoi(userID)
	if err != nil {
		invalidRequestErrorReply(w, "Inappropriate UserId")
		return
	}
	newUser := true

	// Iteration Management
	ok, it := getIterationForUser(userId)
	if !ok {
		//user not found... set iteration to 1
		it = 1
		if err = setIteration(userId, it); err != nil {
			serverErrorReply(w, "Database Failure")
		}
	} else {
		// increase iteration!
		it += 1
		newUser = false
		if err = setIteration(userId, it); err != nil {
			serverErrorReply(w, "Database Failure")
		}
	}

	// Generating keys
	ok, keys := setKeys(userId, it)
	if !ok {
		serverErrorReply(w, "Database Failure")
		return
	}

	// Replying
	if newUser {
		w.WriteHeader(http.StatusCreated)
	} else {
		w.WriteHeader(http.StatusOK)
	}

	b, err := json.Marshal(keys)
	if err != nil {
		serverErrorReply(w, "Convertion Failure")
		return
	}
	w.Write(b)
}

func getValidateHandler(w http.ResponseWriter, r *http.Request) {
	key := r.PathValue("key")

	blob, err := rdb.Get(ctx, fmt.Sprintf("%s:keys:%s", keyPrefix, key)).Result()
	if err == redis.Nil {
		authenticationErrorReply(w, "")
		return
	} else if err != nil {
		serverErrorReply(w, "Database Failure")
		return
	}
	userId := 0
	iteration := 0

	_, err = fmt.Sscanf(blob, "%d:%d", &userId, &iteration)

	if err != nil {
		serverErrorReply(w, "Database Failure")
		return
	}

	// check iteration.
	ok, actual_it := getIterationForUser(userId)
	if !ok {
		serverErrorReply(w, "Server Failure")
		return
	}

	if iteration != actual_it {
		authenticationErrorReply(w, "")
		return
	}

	// iteration match! authentication okay!

	// Invalide la clé actuelle
	rdb.Del(ctx, fmt.Sprintf("%s:keys:%s", keyPrefix, key))

	// Génère une nouvelle clé pour le même user
	ok, nkey := setKey(userId, iteration)
	if !ok {
		serverErrorReply(w, "Database Failure")
		return
	}

	w.WriteHeader(http.StatusOK)
	w.Write([]byte(nkey))
}
