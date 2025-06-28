# Image PHP 8.3 avec Composer et extensions nécessaires
FROM php:8.2-fpm

# Extensions PHP nécessaires
RUN apt-get update && apt-get install -y \
    git zip unzip curl libpq-dev libzip-dev libonig-dev \
    && docker-php-ext-install pdo pdo_pgsql mbstring zip

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html

EXPOSE 8000

RUN groupadd -g 1000 www && useradd -u 1000 -ms /bin/bash -g www www

# Droits pour VS Code
RUN chown -R www:www /var/www/html

USER www