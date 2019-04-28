This project is a testing project build with the help up selenium and java. The project has following capabilities.
1) go to this url -https://www.makemytrip.com
2) Click on flights link and click on round trip
3) Select from: Delhi andTo: Bangalore
4) Select departure date (today’s date) and return date: after 7 days 
5) Click on Search
6) Print total number of records of “Departure Flight” and “Return Flight” on the console
7) Select Non-Stop and 1 Stop filter options and print total number of records of “Departure Flight” and “Return Flight” on the console
8) Select radio button of top 10 options of “Departure Flight” and “Return Flight”
9) Verify the same Departure Flight price and Return Flight price are getting reflected at bottom of the page
10) Verify the correct total amount (Departure Flight price + Return Flight price) is getting reflected correctly

Please find the configuration file in Config folder of the project. This configuration is useful for running the test cases across different browsers and OS. The project only support 2 browsers i.e. chrome or ff(firefox).
Configuration for Windows Machine and need to be changed accordingly for Mac Machine
browser = chrome
os = windows
systemuser = bhaba
systemhost = DESKTOP-BA319U8

