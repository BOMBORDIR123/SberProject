# Этап 1: Сборка Vite-приложения
FROM node:20.18.0-alpine3.20 AS build

WORKDIR /app

COPY package.json package-lock.json* ./
RUN npm install

COPY . .
RUN npm run build

# Этап 2: Продакшн-сервер (Nginx)
FROM nginx:alpine

# Копируем билд из предыдущего этапа
COPY --from=build /app/dist /usr/share/nginx/html

# Если у тебя кастомный конфиг nginx — раскомментируй строку ниже и создай nginx.conf рядом
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
