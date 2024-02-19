# Use an official Node.js runtime as a base image
FROM node:20-alpine

# Set the working directory
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install app dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React app for production
RUN npm run build

# Expose the port where the app will run
EXPOSE 5173

# Specify the command to run on container startup
CMD npm run dev
