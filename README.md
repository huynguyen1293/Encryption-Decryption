# Encryption-Decryption
A simple codec tool using Caesar cipher in Java

The program will take the following arguments (in no particular order):

    -mode   : determines the program’s mode (enc for encryption, dec for decryption)
  
    -key    : an integer key to modify the message
  
    -data   : a text or ciphertext (read from standard input) to encrypt or decrypt
    
    -in     : the full name of a file to read data
    
    -out    : the full name of a file to write the result
    
    -alg    : the algorithm used encode/decode data (shift for shifting algorithm that works only on English letters, 
    unicode for Unicode based algorithm tha works with all characters)
    
These following rules should be accompanied:

  1. If there is no -mode, the program will work in enc mode.
  
  2. If there is no -key, the program will set key = 0.
  
  3. If there is no -data, and there is no -in the program will assume that the data is an empty string.
  
  4. If there is no -out argument, the program will print data to the standard output.
  
  5. If there are both -data and -in arguments, the program will prefer -data over -in.
  
  6. If there is no -alg, the program will use shift as the default algorithm.
  
  # Examples
  Example 1:
  
      java Main -mode enc -in in.txt -out out.txt -key 5 -alg unicode
      
  Example 2:
  
      java Main -mode enc -key 5 -alg unicode -data "Hello world!"
