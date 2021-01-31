import io
import os
from google.cloud import vision
from google.cloud.vision_v1 import types
import json
import re

os.environ["GOOGLE_APPLICATION_CREDENTIALS"]="C:/Users/tarik/mchacks2020-4e33795287ad.json"

client = vision.ImageAnnotatorClient()

# Replace with path to your picture
FILE_NAME = 'sample2.jpg'
FOLDER_PATH = 'C:\\Users\\tarik\\git_projects\\mchacks8'

with io.open(os.path.join(FOLDER_PATH, FILE_NAME), 'rb') as image_file:
    content = image_file.read()

image = types.Image(content=content)
response = client.text_detection(image=image)
texts = response.text_annotations

# Open .json files
with open("drug_dictionary.json") as drug_dict_file:
    drug_dict = json.load(drug_dict_file)

# patient_master.json can be replaced with patient.json to save medications
with open("patient_master.json") as patient_file:
    patient = json.load(patient_file)

# Parse the data
drug_name = None
full_text = []
name_match = None
for text in texts:
    full_text += re.split('\s', text.description)
    if not name_match:
        for d in drug_dict["Drugs"]:
            name_match = re.search(d, text.description, flags=re.IGNORECASE)
            if name_match:
                drug_name = d

if not drug_name:
    print("No medication name found. Please take another picture.")
    exit(2)

# Get the date
months = ['jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec']
date = ""
for i in range(len(full_text)):
    for month in months:
        if re.search(month, full_text[i], flags=re.IGNORECASE) and re.search('[1-3]?[0-9]',full_text[i-1]) and re.search('20[0-2][0-9]',full_text[i+1]):
            date = full_text[i-1] + " " + full_text[i] + " " + full_text[i+1]
            break
        if re.search('[0-1][0-9]/[0-3][0-9]/20[0-2][0-9]', full_text[i]) or re.search('20[0-2][0-9]/[0-1][0-9]/[0-3][0-9]', full_text[i]):
            date = full_text[i]
            break

new_drug = \
    {
        "Name" : drug_name,
        "Dosage" : 200,
        "Administration" : "Bum",
        "Frequency" : 0.5,
        "Quantity": 10,
        "Start date": date,
        "ReferenceNumber" : 9213929,
        "Notes" : "TAKE 1 TABLET UP BUM ONCE EVERY TWO DAYS",
        "FullText" : ' '.join(full_text)
    }

patient['Medications'].append(new_drug)

with open('patient.json', 'w') as dest:
    json.dump(patient, dest, indent=4, sort_keys=True)