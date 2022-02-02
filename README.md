# ScalaJsonFlattener this is an scala wrapper around jackson library to flatten json.
String Json like below  
"{\"abc\":{\"def\":123},\"employees\":[    {\"name\":\"Ram\", \"email\":\"ram@gmail.com\", \"age\":23}, {\"name\":\"Ram1\",\"email\":\"ram1@gmail.com\", \"age\":33}]}"
can be flattened to 
abc.def:123||employees.0.name:"Ram"||employees.0.email:"ram@gmail.com"||employees.0.age:23||employees.1.name:"Ram1"||employees.1.email:"ram1@gmail.com"||employees.1.age:33

