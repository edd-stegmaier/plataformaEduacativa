


- ### arreglo error main.yml linea 62 **No such container: my-app**

-  version anterior muestra error si no habia version previa de my-app: 
``` shell
    # Parar y eliminar cualquier contenedor previo  
        docker stop my-app || true && docker rm my-app || true 
```
- version nueva no muestra error si no existia contenedor my-app:
``` shell
    # Parar y eliminar cualquier contenedor previo
        docker rm -f my-app 2>/dev/null || true 
``` 




