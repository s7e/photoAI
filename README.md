# 📸 photoAI
An AI based Spring Boot application which helps photographers to select the correct camera settings for their desired picture.

## How to run it
* Clone this Git project.
* Make sure it's a Maven project and run Maven to load all dependencies.
* Create an account at https://openai.com and generate an API key at https://platform.openai.com/api-keys.
* Add your API key to the application.properties file.
* Start the application as a Spring Boot app.
* Open http://localhost:8080/ in your browser.

## What it does
* Combines a predefined prompt with your input.
* Sends it to a Large Language Model (LLM) via the OpenAI API.
* Returns tailored camera settings (shutter speed, ISO, aperture) for your desired photo style.

## License
MIT