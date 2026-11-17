
from fastapi import FastAPI

# Create the SmartPool API
app = FastAPI(title="SmartPool API")

# Welcome endpoint
@app.get("/")
def home():
    return {"message": "Welcome to SmartPool API"}

# Check whether the backend is working
@app.get("/health")
def health():
    return {"status": "online", "service": "SmartPool API"}
