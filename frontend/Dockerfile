FROM node:18 AS build
WORKDIR /app
COPY package.json package-lock.json* tsconfig.json postcss.config.js tailwind.config.js ./
COPY public public
COPY src src
RUN npm install --no-audit --no-fund && npm run build

FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
