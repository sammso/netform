
   NetForm Web Application Framework for Java V 0.64
   =================================================

   What is new on 0.64
   ------------------------------------------------------------ 
   - New Components
     + PasswordField
     + CheckBox field
   - TimestampField was empty and null was not allowed. It was valid.
     This was found also on all number fields
   - Button can be attached to Table row, and events from Table components
     are catchable.
   - Table
     + component events are catchable.
     + Bug on Table, It caused error on Table.synchRowNumbers() on
       sertain cases. It is now fixed
     + 937703 Bug on Table.getText() doesn't format correctly fixed
     + JSP inteface Table.getBoolean(..), see JavaDoc
     + Table Validation is working, still needs little bit optimization
   - ListTableModel, didn't update table when using setList()
     + When updating List won't cause out of sync situation
   - SimpleTableModel.resetAll(Object[]) -> setObjects()
   - SimpleTableModel.reset() new method makes SimpleTableModel empty
   - New Method DataSetTableModel.setDataSet()
   - More methods for Timestamp
     + To get dates
     + To get value by specific format
   - Form.getPostAction(), to be used instead of getRequestURI()
     <form method="POST" action="<%=form.getPostAction() %>">
   - Timestamp field can be splitted to Date and Time part on JSP page

   What is new on 0.63
   ------------------------------------------------------------ 
   - Form.execute() optimazation caused Form.startService()
     was called only once.
   - BugFix on example app on table2.jsp/Table2Form.java
   - Form new methods getContextPath(), Form.getRequestURI()
     which also effects how to make JSP
     <form method="POST" action="<%=form.getRequestURI() %>">
   - Fix on Table.insertRowBeforeFirstSelection()
   - Fixes on table
   - Components
     + New Components FloatField, DoubleField
   - New validation system
     + New Validate transfer class
     + When child is not valid then parent is not valid either.
   - ListModel, this is abstract class to model Lists. Can be used with J2EE
     applications. I'm using it with JBoss.
   - Package structure has changed. If you are using Eclipse. CTRL-SHIFT-O
     helps, remember also your JSP pages
   - New column format system.
     + Components can read their format from parent.
       example in table level is possible set column formats or
       it can read global
     + Future default formats can be set on web.xml
     
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
    