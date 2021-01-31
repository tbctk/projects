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
full_text = []
for text in texts:
    full_text += re.split('\s', text.description)



# Get info
drug_name = None
months = ['jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec']
date = "No date found"
dosage = "No dosage found"
for i in range(len(full_text)):
    # Search for drug name and dosage
    if not drug_name:
        for d in drug_dict["Drugs"]:
            if re.search(d, full_text[i], flags=re.IGNORECASE):
                drug_name = d
                dosage = full_text[i+1]

    # Search for date
    if date == "":
        for month in months:
            if re.search(month, full_text[i], flags=re.IGNORECASE) and re.search('[1-3]?[0-9]',full_text[i-1]) and re.search('20[0-2][0-9]',full_text[i+1]):
                date = full_text[i-1] + " " + full_text[i] + " " + full_text[i+1]
            if re.search('[0-1][0-9]/[0-3][0-9]/20[0-2][0-9]', full_text[i]) or re.search('20[0-2][0-9]/[0-1][0-9]/[0-3][0-9]', full_text[i]):
                date = full_text[i]


if not drug_name:
    print("No medication name found. Please take another picture.")
    exit(2)

patient['Medications'].append(
    {
        "Name" : drug_name,
        "Dosage" : dosage,
        "Administration" : "Bum",
        "Frequency" : 0.5,
        "Quantity": 10,
        "Start date": date,
        "ReferenceNumber" : 9213929,
        "Notes" : "TAKE 1 TABLET UP BUM ONCE EVERY TWO DAYS",
        "FullText" : ' '.join(full_text)
    }
)

with open('patient.json', 'w') as dest:
    json.dump(patient, dest, indent=4)