package main

import (
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/redis/go-redis/v9"
)

const listening_port = 7000

func logger(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		log.Println(r.URL.Path)
		next.ServeHTTP(w, r)
	})
}

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

	r.Handle("POST /register/{user_id}", logger(http.HandlerFunc(generateKeysForUser)))
	r.Handle("GET /check/{key}", logger(http.HandlerFunc(getValidateHandler)))
	r.HandleFunc("/", rejecterHandler)

	fmt.Printf("Server listening on :%d\r\n", listening_port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", listening_port), r))
}

// getEnv lit une variable d'environnement ou retourne une valeur par défaut
func getEnv(key, fallback string) string {
	if value, ok := os.LookupEnv(key); ok {
		return value
	}
	return fallback
}
