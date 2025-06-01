package utilities;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class ClassInfoUtility 
{
	public int getAllMethodsCountInAClass(String className)
	{
		try 
		{
            Class<?> c=Class.forName(className);
            Method[] ms=c.getDeclaredMethods();
            return(ms.length);
        } 
		catch (ClassNotFoundException e) 
		{
            throw new RuntimeException("Class not found: " + className);
        }
	}
	
	public int getPublicStaticMethodsCountInAClass(String className)
	{
		try 
		{
            Class<?> c=Class.forName(className);
            Method[] ms=c.getDeclaredMethods();
            int count=0;
            for(Method m:ms) 
			{
            	if(Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers())) 
            	{
                    count++; //increment by 1
                }
			}
			return(count);
        } 
		catch (ClassNotFoundException e) 
		{
            throw new RuntimeException("Class not found: "+className);
        }
	}
	
	public void getAllMethodsInfoInAClass(String className)
	{
		try 
		{
            Class<?> c=Class.forName(className);
            Method[] ms=c.getDeclaredMethods();
            // Print details of each method
            System.out.println("Method details:");
            for(Method m:ms) 
            {
                System.out.println("Method name: "+m.getName());
                System.out.println("Return type: "+m.getReturnType().getName());
                if(Modifier.isStatic(m.getModifiers())) 
				{
					System.out.println("Method is static");
				}
                else
                {
                	System.out.println("Method is non-static");
                }
                // Print parameter types
                Parameter[] ps=m.getParameters();
                System.out.print("Arguments types: ");
                for(Parameter p:ps) {
                    System.out.println(p.getType().getName());
                }
                System.out.println("\n");
            }
        } 
		catch(ClassNotFoundException e) 
		{
            throw new RuntimeException("Class not found: " + className);
        }
	}
	
	public void getPublicStaticMethodsInfoInAClass(String className)
	{
		try 
		{
            Class<?> c=Class.forName(className);
            Method[] ms=c.getDeclaredMethods();
            // Print details of each method
            System.out.println("Method details:");
            for(Method m:ms) 
            {
            	if(Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers())) 
            	{
					System.out.println("Method name: "+m.getName());
					System.out.println("Return type: "+m.getReturnType().getName());
					// Print parameter types
					Parameter[] ps=m.getParameters();
					System.out.print("Arguments types: ");
					for(Parameter p:ps) {
						System.out.println(p.getType().getName());
					}
					System.out.println("\n");
				}
            }
        } 
		catch (ClassNotFoundException e) 
		{
            throw new RuntimeException("Class not found: " + className);
        }
	}
	
	public void getSpecificMethodInfoInAClass(String className, String methodName)
	{
		try 
		{
            Class<?> c=Class.forName(className);
            Method[] ms=c.getDeclaredMethods();
            System.out.println("Method details:");
            int flag=0;
            for(Method m:ms) 
            {
            	if(m.getName().equals(methodName))
				{
					flag=1;
					System.out.println("Method name: " +m.getName());
					System.out.println("Return type: " +m.getReturnType().getName());
					if(Modifier.isStatic(m.getModifiers()))
					{
						System.out.println("Method is static");
					}
					else
					{
						System.out.println("Method is non-static");
					}
					// Print parameter types
					Parameter[] ps=m.getParameters();
					System.out.print("Arguments types: ");
					for (Parameter p:ps) {
						System.out.println(p.getType().getName());
					}
				}
            }
			if(flag==0)
			{
				throw new RuntimeException("Method not found: "+methodName);
			}
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: "+className);
        }
	}
}
