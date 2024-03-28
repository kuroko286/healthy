#!/bin/sh
echo "Stopping redis..."
docker container stop localredis
# docker container rm -f localredis
echo "----------------------------------"
echo "stopping spring app..."
# Define the port number
PORT_BACKEND=8080
# Find the Process ID (PID) associated with the port
PID=$(netstat -ano | findstr ":$PORT_BACKEND" | awk '{print $5}')
# Extract the PID from the output
PID=${PID##*:}
# Check if PID is not empty
if [ -n "$PID" ]; then
    echo "Found process running on port $PORT_BACKEND with PID $PID"
    
    # Terminate the process
    taskkill /F /PID $PID
    echo "Process terminated."
else
    echo "No process found running on port $PORT_BACKEND."
fi

echo "----------------------------------"
echo "stopping frontend..."
PORT_FRONTEND=3000
# Find the Process ID (PID) associated with the port
PID=$(netstat -ano | findstr ":$PORT_FRONTEND" | awk '{print $5}')
# Extract the PID from the output
PID=${PID##*:}
# Check if PID is not empty
if [ -n "$PID" ]; then
    echo "Found process running on port $PORT_FRONTEND with PID $PID"
    
    # Terminate the process
    taskkill /F /PID $PID
    echo "Process terminated."
else
    echo "No process found running on port $PORT_FRONTEND."
fi
sleep 50000000