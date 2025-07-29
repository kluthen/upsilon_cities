package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"

	"github.com/google/uuid"
	"github.com/redis/go-redis/v9"
)

func setup() {
	rdb = redis.NewClient(&redis.Options{
		Addr:     getEnv("REDIS_HOST", "redis") + ":" + getEnv("REDIS_PORT", "6379"),
		Password: "", // pas de mot de passe par défaut
		DB:       0,
	})

	// Test de connexion Redis
	_, err := rdb.Ping(ctx).Result()
	if err != nil {
		log.Fatalf("Erreur de connexion Redis: %v", err)
	}

	rdb.FlushDB(ctx)
}

func shutdown() {
	rdb.FlushDB(ctx)
	rdb.Close()
}

func TestMain(m *testing.M) {
	setup()
	code := m.Run()
	shutdown()
	os.Exit(code)
}

func TestGenerateHandlerSuccess(t *testing.T) {
	req, err := http.NewRequest("POST", "/register/1", nil)
	req.SetPathValue("user_id", "1")
	if err != nil {
		t.Fatal(err) // this is weird.
		return
	}

	rr := httptest.NewRecorder()

	handler := http.HandlerFunc(generateKeysForUser)

	handler.ServeHTTP(rr, req)

	if status := rr.Code; status != http.StatusCreated {
		t.Errorf("Expected reply to be 201 Created, got %v", status)
	}

	var res [nbKeys]string
	err = json.Unmarshal(rr.Body.Bytes(), &res)
	if err != nil {
		t.Errorf("Expected body to contain an array of %d strings got something else: %v %v", nbKeys, rr.Body.String(), err)
	}

}

func TestGenerateHandlerRegenerateSuccess(t *testing.T) {
	// ensure there is already a set of keys bound to the test user 1.
	setIteration(1, 1)
	setKeys(1, 1)

	req, err := http.NewRequest("POST", "/register/1", nil)
	req.SetPathValue("user_id", "1")
	if err != nil {
		t.Fatal(err) // this is weird.
		return
	}

	rr := httptest.NewRecorder()

	handler := http.HandlerFunc(generateKeysForUser)

	handler.ServeHTTP(rr, req)

	if status := rr.Code; status != http.StatusOK {
		t.Errorf("Expected reply to be 200 OK, got %v", status)
	}

	var res [nbKeys]string
	err = json.Unmarshal(rr.Body.Bytes(), &res)
	if err != nil {
		t.Errorf("Expected body to contain an array of %d strings got something else: %v %v", nbKeys, rr.Body.String(), err)
	}
}

func TestValidateHandlerSuccess(t *testing.T) {
	// necessary setup
	setIteration(1, 1)
	ok, key := setKey(1, 1)
	if !ok {
		t.Fatal("Should have been able to register a key for user.")
	}

	req, err := http.NewRequest("GET", fmt.Sprintf("/check/%s", key), nil)
	req.SetPathValue("key", key)
	if err != nil {
		t.Fatal(err) // this is weird.
		return
	}

	rr := httptest.NewRecorder()

	handler := http.HandlerFunc(getValidateHandler)

	handler.ServeHTTP(rr, req)

	if status := rr.Code; status != http.StatusOK {
		t.Errorf("Expected reply to be 200 OK, got %v", status)
	}

	res, err := uuid.ParseBytes(rr.Body.Bytes())
	if err != nil {
		t.Errorf("Expected body to contain a new key got something else: %v - %v, error: %v", rr.Body.String(), res, err)
	}
}
