FROM node:22-alpine
WORKDIR /opt/app
COPY package.json package-lock.json* ./
RUN npm ci --omit=dev
COPY . .
CMD ["node", "app.js"]
EXPOSE 9999
