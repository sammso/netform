
   NetForm Web Application Framework for Java V 0.61s
   =================================================

   What is new on 0.61
   -------------------------------------------------------------
   - Table programming interface is changed (easier, better)
     - DisplayRow (removed) class removed
     - TableModelComponent removed
       - Functionality is made through DataComponent
     - API Change Table.setTableModelComponent -> Table.setModelComponent
   - Component's are based on new ComponentData data handling.
     - It is optional
     - It is done because Table. Every component now has option
       to store it's data to 
   - Stared to developing STATE machine to control usage of method.
     - This methods that some functionality is generating Exception
       when called on wrong state
        + Form.FORM_STATE_..
      see more on JavaDoc on Form and Components section
   - Component is isValid by default.
   - Components removed
     - NumberField, Replaced bye IntegerField etc.
     - ToggleField, to be replace by BooleanField
   - New Components, IntegerField, LongField, 
 
   
   What is new on 0.60
   -------------------------------------------------------------
   - Developement on
     - Login handling
     - Browsers back button handling
     - Form objects can use also ServletContext
     - Table handling (Not satisfied how it is done) also
       0.51 had serious bug inside.
   
   What is new on 0.51
   -------------------------------------------------------------
   - Developement on
     - is valid handing
     - test application look and feel
     - fixes on table and other components
   
   Author
   -------------------------------------------------------------
   Sampsa Sohlman (http://sampsa.sohlman.com)
    - All comments are more than welcome   
        
   Goal of project
   ---------------
   
   Goal of NetForm Web Application Framework for Java is to be easy, productive and 
   maintainable way to create complex HTML application forms and logic.
   
   Directory structure
   -------------------
    - source contains sources for netform libraries
      - source/netform Main Framework source
      - source/dataset NetForm-DataSet connectors 
        (DataSet is other my project http://dataset.sohlman.com)
    - webapp contains webapplication sources for testing purposes
    - lib contains netform libraries and compiled web application war file.
    - javadocs is for javadocs 
    