Sample Carbon Tenant Resource Test
========================================

Create a tenat : e.g. domain = test.com
deploy the web app in the super tenat ( carbon.super ) 
login to the tenant and add a resources in the _system > config registry path
perform the following curl call

CURL call : curl --data "action=get&path=<path>&resource=<resource>&domain=<tenant domain>" -v http://192.168.144.1:9763/TestCarbonTenantResource/HelloServlet


e.g. : curl --data "action=get&path=/test&resource=name&domain=test.com" -v http://192.168.144.1:9763/TestCarbonTenantResource/HelloServlet
