package main

import (
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/redis/go-redis/v9"
)

func main() {
	// Connexion Redis avec variables d'env
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

	r := http.NewServeMux()

	r.HandleFunc("POST /register/{user_id}", generateKeysForUser)
	r.HandleFunc("GET /check/{key}", getValidateHandler)

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
