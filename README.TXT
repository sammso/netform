
   NetForm Web Application Framework for Java V 0.63
   =================================================

   What is new on 0.63
   ------------------------------------------------------------ 
   - Form new methods getContextPath(), Form.getRequestURI()
     which also effects how to make JSP
   - Fix on Table.insertRowBeforeFirstSelection()
   
   What is new on 0.62
   ------------------------------------------------------------ 
   - Form.execute() optimazation caused Form.startService()
     was called only once.
   - BugFix on example app on table2.jsp/Table2Form.java

   What is new on 0.62
   -------------------------------------------------------------
   - Table can be used inside Table as Component
     + This is exiting feature. See the example table2.jsp
   - Bug fixes on Table
   - TableModel can be connected to multible tables.
   - HTML escape improvements
   - SimpleTableModel.add() adds row to last as it should
   
   
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
       to store and retrieve data through ComponentData
   - Stared to developing STATE machine to control usage of methods.
     - This methods that some functionality is generating Exception
       when called on wrong state. Some methods are designed to 
       work only example on templating side. So mechanism is 
       needed to prevent programmers to use methods on wrong place.
   - Component is isValid by default.
   - Removed components
     - NumberField, Replaced bye IntegerField etc.
     - ToggleField, to be replace by BooleanField
   - New Components, IntegerField, LongField
   - HTML escape implemented
   
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
    