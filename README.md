## Proyecto desarrollado en las actividades de Desarrollo Cloud Native

integrado con Github Actions, AWS, Docker y Base de datos Oracle Cloud.



### Configuracion de Base de datos Oracle

- repositorio github: se incluyen username y password como secrets para usarlos en github actions
- en main.yml ln 64: se le pasan variables de entorno y ubicacion del wallet en la instancia ec2
- aplication.properties: recibe username y password como variables de entorno y define ruta wallet en contenedor
- en pom.xml: se agregan dependencias: ojdbc11, oraclepki, osdt_core, osdt_cert