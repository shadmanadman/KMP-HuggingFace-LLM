from fastapi import FastAPI, Request
from transformers import pipeline

app = FastAPI()

summarizer = pipeline("summarization", model="sshleifer/distilbart-cnn-12-6")

@app.post("/summarize")
async def summarize(request: Request):
    data = await request.json()
    text = data.get("text", "")

    if not text:
        return {"error": "No text provided"}

    summary = summarizer(text, max_length=100, min_length=20, do_sample=False)
    return {"summary": summary[0]['summary_text']}
