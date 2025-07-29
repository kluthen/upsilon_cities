# JWT

JSON Web Token service ensure that a user is correctly logged already. It is like a SSO.
The user first has to authenticate somewhere, then he is provided a bunch of keys, that will need to be provided on each subsequent request it does. 
These keys will then be checked out in this service for validity. 
Should the key be valid, it will be purged from the accound and another one will be added and returned.

## Use Cases & Routes

### Initialization

```mermaid
sequenceDiagram
    participant Caller
    Participant JWT
    Participant REDIS
    Caller ->> JWT: POST /register/{user_id}
    JWT ->> REDIS: Set jwt:{user_id}:iteration = 1
    JWT ->> REDIS: Set jwt:keys:{key} = user_id:iteration...
    JWT -->> Caller: 200 JSON [key1,...]
```

This first call sets up the database with appropriate information.

**Note:** TTL is set to 1 hour. Afterwich, individual keys will be expurged.

Calling it once more increase iteration by one.

#### Iteration

Iteration check allow to discard a whole bunch of keys for a user (invalidating them all) 

### Check

```mermaid
sequenceDiagram
    participant Caller
    Participant JWT
    Participant REDIS
    Caller ->> JWT: GET /check/{key}
    JWT ->> REDIS: Get jwt:keys:{key} 
    alt Found
        REDIS -->> JWT: iteration + user_id
        JWT ->> REDIS: Get jwt:{user_id}:iteration
        REDIS -->> JWT: iteration value
        alt Iteration Match
            JWT ->> REDIS: update users keys (drop used key, add a new key), add a key in jwt:keys
            JWT -->> Caller: 200, new_key
        else Iteration Missmatch
            JWT -->> Caller: 403
        end
    else Not Found
        REDIS -->> JWT: error
        JWT -->> Caller: 403
    end
```
