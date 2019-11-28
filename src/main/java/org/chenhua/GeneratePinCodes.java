package org.chenhua;

import java.security.SecureRandom;
import java.util.HashSet;

/**
 * create a library which generates a batch of 1000 randomly generated pin codes.
 * 1.each pin code in the batch needs to be unique.
 * 2.the pin code cannot have two consecutive numbers which are the same, e.g. 1186,8229,or 5188
 * 3.the pin code cannot have three consecutive numbers which are sequential, e.g. 5123,4562
 * 
 * @author HuaChen
 * 10.Oct.2019
 */
public class GeneratePinCodes {
	
	public final static int DEFAULT_LENGTH = 4;
	
	private int countPinCodes = 0;
	
	private int pincodeLength = DEFAULT_LENGTH;
	
	public int getPincodeLength() {
		return pincodeLength;
	}

	public void setPincodeLength(int pincodeLength) {
		this.pincodeLength = pincodeLength;
	}

	public GeneratePinCodes(int countPinCodes) {
		super();
		this.countPinCodes = countPinCodes;
	}

	/**
	 * Generate a batch of 1000 randomly generated pin codes which consist by 4 numbers
	 * Return: HashSet<String> 
	 */
	public HashSet<String> generateUniquePinCodes() throws Exception {
		
		HashSet<String> pinCodesSet = new HashSet<String>();
		
		// Use HashSet to store a collection of unique pin codes
		while (pinCodesSet.size()<countPinCodes)
		{
			String strPinCode = produceSinglePinCodeBySecureRandom(pincodeLength);
			if ((strPinCode != null)
					&&(strPinCode.length() == pincodeLength))
			{
				// If have 2 consecutive numbers which are the same, abandon it 
				if (!validateAndNo2SameConsecutive(strPinCode))
				{
					continue;
				}
				// If have 3 consecutive numbers which are sequential, abandon it
				if (!validateAndNo3SequelConsecutive(strPinCode))
				{
					continue;
				}
					
				pinCodesSet.add(strPinCode);			
			}
		}
		return pinCodesSet;
	}
	
	/**
	 * Produces cryptographically strong random values by using a cryptographically strong pseudo-random number generator
	 * Return: String 
	 */
	private String produceSinglePinCodeBySecureRandom(int pinCodeLenth) throws Exception {
		
		if (pinCodeLenth <= 0)
		{
			return null;
		}
		
		String strPinCode = null;
		
		//Get the default seed from /dev/urandom, which collect environmental noise from device drivers and other sources
		//Then uses the SHA1PRNG algorithm to generate random values
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");;
		
		//Maximum boundary of the pin code		
		int maxBound = (int)Math.pow(10, pinCodeLenth);
		
		int intPinCode = 0;
		
		//generate pin code
		do
		{
			intPinCode = random.nextInt(maxBound);
		}
		while (intPinCode < 0);
	
		strPinCode = Integer.toString(intPinCode);
		
		//if the pin code is shorter than pinCodeLenth, fill with '0' at the head until the pinCodeLenth
		for (int i=1;i<pinCodeLenth;i++)
		{
			if (intPinCode < (int)Math.pow(10, pinCodeLenth-i))
			{
				strPinCode = "0" + strPinCode;
			}
		}
		
		return strPinCode;
	}
	
	/**
	 * Validate string format and check if the string doesn't contain 2 consecutive numbers which are the same
	 * Return: 
	 * 		if pass, return true 
	 * 		if not, return false
	 */
	private Boolean validateAndNo2SameConsecutive(String str)
	{
		boolean ret = false;
		
		if (str == null)
		{
			ret = false;
			return ret;
		}
		
		str = str.trim();
		if (str.length() == 0)
		{
			ret = false;
			return ret;
		}
		
		for(int i=0;i<str.length();i++) 
		{
			if ((str.charAt(i)<'0')||(str.charAt(i)>'9'))
			{
				ret = false;
				return ret;
			}
		}

		for (int i=0;i<str.length()-1;i++)
		{
			if ( str.charAt(i) == str.charAt(i+1))
			{
//				System.out.println("2Consecutive:"+str);
				ret = false;
				return ret;
			}	
		}
		ret = true;
		return ret;
	}
	
	/**
	 * Validate string format and check if the string doesn't contain 3 consecutive numbers which are sequential
	 * Return: 
	 * 		if pass, return true 
	 * 		if not, return false
	 */
	private Boolean validateAndNo3SequelConsecutive(String str)
	{
		boolean ret = false;
		
		if (str == null)
		{
			ret = false;
			return ret;
		}
		
		str = str.trim();
		if (str.length() == 0)
		{
			ret = false;
			return ret;
		}
		
		for(int i=0;i<str.length();i++) 
		{
			if ((str.charAt(i)<'0')||(str.charAt(i)>'9'))
			{
				ret = false;
				return ret;
			}
		}

		for (int i=0;i<str.length()-2;i++)
		{
			int currentAscii = Integer.valueOf(str.charAt(i));
			int nextAscii = currentAscii + 1;
			int nextNextAscii = currentAscii + 2;
			if ((Integer.valueOf(str.charAt(i+1)) == nextAscii)
					&&(Integer.valueOf(str.charAt(i+2)) == nextNextAscii))
			{
//				System.out.println("3Consecutive:"+str);
				ret = false;
				return ret;
			}
		}
		ret = true;
		return ret;
	}

}
