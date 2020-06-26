
# Proteja Brasil  
  
## Configuration  
  
Initialize the git submodules:  
  
`git submodule init`  
  
Update the registered submodules:  
  
`git submodule update`  
  
The project requires a `google-services.json` (br.com.ilhasoft.protejaBrasil) file.

The project requires a string resources file with the following keys:

- **google_maps_key**: Maps SDK API key;

  [https://developers.google.com/maps/documentation/android-sdk/get-api-key/](https://developers.google.com/maps/documentation/android-sdk/get-api-key/)

- **api_base_url**: API url to get information about the protection network, organ categories, types of violation and feedback;

- **api_base_token**: Token to the above API;

- **api_report_url**: API url to report violations;

- **safernet_base_url**: API url to report violations through internet;

- **safernet_token**: Token to the above API;

- **api_violated_women_report_url**: API url to report violation against women;

- **api_violated_women_theme_id**: Internal ID to the above API.
