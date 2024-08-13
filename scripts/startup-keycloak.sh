docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.1 start-dev

# scp -r /Users/ryan/IdeaProjects/hobbies/docker-compose.yml /Users/ryan/IdeaProjects/hobbies/Dockerfile /Users/ryan/IdeaProjects/hobbies/nginx/ root@142.93.10.34:/root/hobbies
docker-compose run --rm certbot certonly --webroot --webroot-path=/var/www/certbot --email ryangst.hire@gmail.com --agree-tos --no-eff-email -d unnamd.cloud -d www.unnamd.cloud
