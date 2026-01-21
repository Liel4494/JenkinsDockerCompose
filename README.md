
# Must Run Before Docker-Compose Up: #

 1. Create Certificate (In the 'docker-compose.yaml' file folder):
   ```
   mkdir certs
   openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
     -keyout certs/privkey.pem \
     -out certs/fullchain.pem \
     -subj "/C=US/ST=State/L=City/O=Organization/OU=DevOps/CN=localhost"
  ```
