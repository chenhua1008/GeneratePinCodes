/**
 * 
 */
package org.chenhua;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;

/**
 * @author HuaChen
 *
 */
public class GeneratePinCodesTest {

	GeneratePinCodes genPinCodes = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//generate 1000 pin codes, length 4
		genPinCodes = new GeneratePinCodes(1000);
		genPinCodes.setPincodeLength(4);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateUniquePinCodes() {
		
		HashSet<String> pinCodesSet = null;
		
		try {
			pinCodesSet = genPinCodes.generateUniquePinCodes();
			System.out.println(pinCodesSet);
			Assert.assertEquals(4, genPinCodes.getPincodeLength());
			
			// Test quantity
			Assert.assertEquals(1000, pinCodesSet.size());
			
			HashSet<String> tmpSet = new HashSet<String>();
					
			Iterator<String> it = pinCodesSet.iterator();			
			while(it.hasNext()) {
				String str = it.next();
				
				// Test Unique, use HashSet, if not unique, hashset.add return false
				Assert.assertTrue(tmpSet.add(str));
				
				// Test format - length:4
				Assert.assertEquals(4, str.length());					
				
				// Test format - numbers:0-9
				for (int i=0;i<str.length();i++)
				{
					Assert.assertTrue((str.charAt(i)>='0')&&(str.charAt(i)<='9'));
				}
				
				// Test format - do not have two consecutive numbers which are the same
				for (int i=0;i<str.length()-1;i++)
				{
					Assert.assertTrue(str.charAt(i) != str.charAt(i+1));
				}
				
				// Test format - do not have three consecutive numbers which are sequential
				for (int i=0;i<str.length()-2;i++)
				{
					Assert.assertFalse((Integer.valueOf(str.charAt(i+1)) == Integer.valueOf(str.charAt(i))+1)
							&&(Integer.valueOf(str.charAt(i+2)) == Integer.valueOf(str.charAt(i))+2));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testProduceSinglePinCodeBySecureRandom() {
		
		try {
			// Use reflection to get the private method
			Class<GeneratePinCodes> classGPC = GeneratePinCodes.class;
			Constructor<GeneratePinCodes> con = classGPC.getConstructor(int.class);
			GeneratePinCodes obj = (GeneratePinCodes) con.newInstance(1000);			
			Method methodProduceSinglePinCodeBySecureRandom = classGPC.getDeclaredMethod("produceSinglePinCodeBySecureRandom", int.class);
			methodProduceSinglePinCodeBySecureRandom.setAccessible(true);
			
			// Check input <0,=0,>0
			Assert.assertNull(methodProduceSinglePinCodeBySecureRandom.invoke(obj, -1));
			Assert.assertNull(methodProduceSinglePinCodeBySecureRandom.invoke(obj, 0));
			Assert.assertNotNull(methodProduceSinglePinCodeBySecureRandom.invoke(obj, 1));
			Assert.assertNotNull(methodProduceSinglePinCodeBySecureRandom.invoke(obj, 100));
			
			String strPinCode4 = (String) methodProduceSinglePinCodeBySecureRandom.invoke(obj, 4);
			Assert.assertNotNull(strPinCode4);
			// Test format - length:4
			Assert.assertEquals(4, strPinCode4.length());
			// Test format - numbers:0-9
			for (int i=0;i<strPinCode4.length();i++)
			{
				Assert.assertTrue((strPinCode4.charAt(i)>='0')&&(strPinCode4.charAt(i)<='9'));
			}
			
			methodProduceSinglePinCodeBySecureRandom.setAccessible(false);	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
//		System.out.println(genPinCodes.produceSinglePinCodeBySecureRandom(4));
	}
	
	@Test
	public void testValidateAndNo2SameConsecutive() {
		
		try {
			// Use reflection to get the private method
			Class<GeneratePinCodes> classGPC = GeneratePinCodes.class;
			Constructor<GeneratePinCodes> con = classGPC.getConstructor(int.class);
			GeneratePinCodes obj = (GeneratePinCodes) con.newInstance(1000);			
			Method methodValidateAndNo2SameConsecutive= classGPC.getDeclaredMethod("validateAndNo2SameConsecutive", String.class);
			methodValidateAndNo2SameConsecutive.setAccessible(true);
			
			// Check
			Assert.assertTrue((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, null));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, ""));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, " 	  "));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "% *"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "abcd"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1234dsf"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "abcd"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "22"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1 1"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "333"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "0000"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "55555"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1134"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1334"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1233"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "12344"));
			Assert.assertFalse((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1231255"));	
			Assert.assertTrue((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1"));
			Assert.assertTrue((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1314"));
			Assert.assertTrue((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "12345"));
			Assert.assertTrue((Boolean) methodValidateAndNo2SameConsecutive.invoke(obj, "1234"));
			
			methodValidateAndNo2SameConsecutive.setAccessible(false);	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValidateAndNo3SequelConsecutive() {
		
		try {
			// Use reflection to get the private method
			Class<GeneratePinCodes> classGPC = GeneratePinCodes.class;
			Constructor<GeneratePinCodes> con = classGPC.getConstructor(int.class);
			GeneratePinCodes obj = (GeneratePinCodes) con.newInstance(1000);			
			Method methodValidateAndNo3SequelConsecutive= classGPC.getDeclaredMethod("validateAndNo3SequelConsecutive", String.class);
			methodValidateAndNo3SequelConsecutive.setAccessible(true);
			
			// Check
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, null));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, ""));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, " 	  "));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "% *"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "abcd"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1234dsf"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "abcd"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1 1"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1233"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "0234"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "02456"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "12344"));
			Assert.assertFalse((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1231255"));	
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "22"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "333"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "0000"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "55555"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "0245"));		
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1134"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1334"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1314"));
			Assert.assertTrue((Boolean) methodValidateAndNo3SequelConsecutive.invoke(obj, "1134"));
			
			methodValidateAndNo3SequelConsecutive.setAccessible(false);	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	//generate 100 pin codes, length 10 
	public void testGenerateUniquePinCodes100Length10() {
		HashSet<String> pinCodesSet = null;
		
		try {
			GeneratePinCodes genPinCodes100Length10 = new GeneratePinCodes(100);
			genPinCodes100Length10.setPincodeLength(10);
			pinCodesSet = genPinCodes100Length10.generateUniquePinCodes();
			
			Assert.assertEquals(10, genPinCodes100Length10.getPincodeLength());
			System.out.println(pinCodesSet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
