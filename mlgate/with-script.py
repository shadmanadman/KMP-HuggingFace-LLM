
import sys
from transformers import pipeline

text = sys.argv[1] if len(sys.argv) > 1 else ""

if text:
    summarizer = pipeline("summarization", model="sshleifer/distilbart-cnn-12-6")
    summary = summarizer(text, max_length=100, min_length=20, do_sample=False)
    print(summary[0]['summary_text'])
else:
    print("No text provided")