package main

import (
	"context"
	"fmt"
	"log"
	"net/http"
	"os"
	"time"

	"github.com/gorilla/mux"
	"github.com/redis/go-redis/v9"
)

var (
	ctx = context.Background()
	rdb *redis.Client
)

// key prefix pour Redis
const keyPrefix = "keygen:"

// generateKey génère une nouvelle clé (ici un exemple simple, à remplacer)
func generateKey(userID string) string {
	// Ici on génère une clé simple, tu peux améliorer avec UUID ou autre
	return fmt.Sprintf("%s:%d", userID, time.Now().UnixNano())
}

func postKeygenHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	userID := vars["user_id"]

	newKey := generateKey(userID)

	err := rdb.Set(ctx, keyPrefix+newKey, userID, 24*time.Hour).Err() // expire en 24h
	if err != nil {
		http.Error(w, "Erreur Redis: "+err.Error(), http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusCreated)
	w.Write([]byte(newKey))
}

func getValidateHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	key := vars["key"]

	userID, err := rdb.Get(ctx, keyPrefix+key).Result()
	if err == redis.Nil {
		http.Error(w, "Clé invalide", http.StatusNotFound)
		return
	} else if err != nil {
		http.Error(w, "Erreur Redis: "+err.Error(), http.StatusInternalServerError)
		return
	}

	// Invalide la clé actuelle
	rdb.Del(ctx, keyPrefix+key)

	// Génère une nouvelle clé pour le même user
	newKey := generateKey(userID)
	rdb.Set(ctx, keyPrefix+newKey, userID, 24*time.Hour)

	w.WriteHeader(http.StatusOK)
	w.Write([]byte(fmt.Sprintf("UserID: %s\nNewKey: %s", userID, newKey)))
}

func deleteInvalidateHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	key := vars["key"]

	deleted, err := rdb.Del(ctx, keyPrefix+key).Result()
	if err != nil {
		http.Error(w, "Erreur Redis: "+err.Error(), http.StatusInternalServerError)
		return
	}
	if deleted == 0 {
		http.Error(w, "Clé non trouvée", http.StatusNotFound)
		return
	}

	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Clé invalidée"))
}

func main() {
	// Connexion Redis avec variables d'env
	rdb = redis.NewClient(&redis.Options{
		Addr:     getEnv("REDIS_HOST", "localhost") + ":" + getEnv("REDIS_PORT", "6379"),
		Password: "", // pas de mot de passe par défaut
		DB:       0,
	})

	// Test de connexion Redis
	_, err := rdb.Ping(ctx).Result()
	if err != nil {
		log.Fatalf("Erreur de connexion Redis: %v", err)
	}

	r := mux.NewRouter()

	r.HandleFunc("/keygen/{user_id}", postKeygenHandler).Methods("POST")
	r.HandleFunc("/validate/{key}", getValidateHandler).Methods("GET")
	r.HandleFunc("/invalidate/{key}", deleteInvalidateHandler).Methods("DELETE")

	fmt.Println("Server listening on :9000")
	log.Fatal(http.ListenAndServe(":9000", r))
}

// getEnv lit une variable d'environnement ou retourne une valeur par défaut
func getEnv(key, fallback string) string {
	if value, ok := os.LookupEnv(key); ok {
		return value
	}
	return fallback
}
