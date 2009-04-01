package com.thesmilingpenguin.albhedroid;

public class Cipherer {
  
  private static Mode cipherMode;
  public static void setCipherMode(Mode mode) {
    cipherMode = mode;
  }
  
  public static String cipher(String input) 
      throws CipherException {
    int inputLength = input.length();
    StringBuffer output = new StringBuffer(inputLength);
   
    char inputChar;
    int index;
    boolean ignoreCharacter = false;
    for (int i = 0; i < inputLength; i++) {
      inputChar = input.charAt(i);
      
      if (inputChar == cipherMode.ignoreStartChar) {
        if (ignoreCharacter)
          throw new CipherException(i+1, inputChar);
        
        ignoreCharacter = true;
      } else if (inputChar == cipherMode.ignoreEndChar) {
        if (!ignoreCharacter)
          throw new CipherException(i+1, inputChar);
        
        ignoreCharacter = false;
      } else if (!ignoreCharacter) {
        index = cipherMode.referenceAlphabet.indexOf(inputChar);
        try {
          char outputChar = cipherMode.charMap.charAt(index);
          output.append(outputChar);
        } catch (IndexOutOfBoundsException e) {
          /* inputChar cannot be ciphered using this charMap */
          output.append(inputChar);
        }
      } else {
        /* ignore block is active, do not cipher inputChar */
        output.append(inputChar);
      }
    }
    
    if (ignoreCharacter)
      throw new CipherException("Unexpected end of input, expected '" + cipherMode.ignoreEndChar + "'");
    
    return output.toString();
  }
  
  /* Cipher mode */
  public static enum Mode { 
    ALBHED_TO_ENGLISH ("epstiwknuvgclrybxhmdofzqajEPSTIWKNUVGCLRYBXHMDOFZQAJ"),
    ENGLISH_TO_ALBHED ("ypltavkrezgmshubxncdijfqowYPLTAVKREZGMSHUBXNCDIJFQOW");
  
    private final String charMap;
    private final String referenceAlphabet;
    private final char ignoreStartChar;
    private final char ignoreEndChar;
    
    private Mode(String charMap) {
      this.charMap = charMap;
      this.referenceAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
      this.ignoreStartChar = '{';
      this.ignoreEndChar = '}';
    }
  }
  
}

class CipherException extends Exception {
  private static final long serialVersionUID = 2179084201511301845L;

  public CipherException(int index, char c) {
    super("Unexpected '" + c + "' at position " + index);
  }
  
  public CipherException(String msg) {
    super(msg);
  }
}
