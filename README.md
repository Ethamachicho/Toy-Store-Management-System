                    TOY STORE DATA FILES FORMAT GUIDE

The Toy Store Management System uses two text files to store data:
1. stores.txt - Contains all store information
2. toys.txt - Contains all toy information

You can manually edit these files to add, modify, or remove stores and toys.
The program will automatically load your changes when it starts.


                              STORES.TXT FORMAT

Format: city|hours

Each line represents one store. The pipe character (|) separates the fields.

Example:
Los Angeles|9AM - 9PM
San Diego|10AM - 8PM
Phoenix|9AM - 10PM

Rules:
- Each store must be on a single line
- Fields are separated by a pipe character (|)
- City name cannot be empty
- Hours cannot be empty
- Lines starting with # are comments and will be ignored
- Empty lines are ignored


                               TOYS.TXT FORMAT

Format: name|isDisney|stock|price|blackFriday

Each line represents one toy. The pipe character (|) separates the fields.

Example:
Buzz Lightyear|true|20|34.99|true
Hot Wheels Pack|false|50|12.99|false

Field Descriptions:
- name: The name of the toy (cannot be empty)
- isDisney: true or false (case-sensitive)
- stock: Number of items in stock (must be 0 or greater)
- price: Price of the toy (must be 0 or greater, can include decimals)
- blackFriday: true or false (case-sensitive)

Rules:
- Each toy must be on a single line
- Fields are separated by a pipe character (|)
- Boolean values must be exactly "true" or "false" (lowercase)
- Stock and price must be valid numbers
- Stock and price cannot be negative
- Lines starting with # are comments and will be ignored
- Empty lines are ignored


                              IMPORTANT NOTES

1. The program automatically saves changes when you:
   - Add a new store or toy through the menu
   - Update an existing store or toy through the menu
   - Exit the program

2. If you manually edit the files:
   - Make sure to follow the exact format
   - Save the files before running the program
   - The program will load your changes on startup

3. If the files don't exist:
   - The program will create them with default data
   - You can then edit them as needed

4. Invalid lines in the files will be skipped with a warning message

5. Comments (lines starting with #) are preserved when the program saves
