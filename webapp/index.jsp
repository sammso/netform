					<jsp:include page="header.jsp" />
						<h1>NetForm test application</h1>
						<h2>Introduction</h2>
						<p>NetForm example application is test and show case of NetForm framework 
						features and capabilities. </p>
						<h2>Fields form(<a href="fields.jsp">fields.jsp</a>)</h2>
						<p>Fields form is example of NetForm fields. There is Components like Button, TextField, NumberField, TimestampField and Table.</p>
						<p>This example also is showing the form ability to validate itself from 
						components. Form also don't let you go to <a href="table.jsp">Table form</a> until your the form is validated. This is done by redirecting you back to Fieds form. 
						This feature can be used as example to segure that user have save changes before leaving form. 
						Form is validated everytime when button is pressed or page contest is send to server.</p>
						<h2>Table form (<a href="table.jsp">table.jsp</a>)</h2>
						<p>One of the power of the Netform is it's ability maintain table form data with Table component. 
						This table component is exactly same as used in Fields example, but it has been layouted little bit different way and also TextFields has been connected to
						it's columns. At this point table don't use validation mechanisms, but it is still there.
						</p>
					<jsp:include page="footer.jsp" />						
							
							