
README FORESTA
============

El presente proyecto es un prototipo funcional para la final del Reto **#RetoForestal** y es desarrollado por [Nicotina Estudio](http://www.nicotinaestudio.com). 

##Descripción

**FORESTA** será una plataforma en tiempo real de administración de información globalizada que almacenara toda la data de cada uno de los roles en un cluster de base de datos no relaciona (MongoDB).

Los procesos se digitalizarán evitando el uso de papeleo. Funcionara para web (FORESTA) y teléfonos inteligentes Android (FORSTA Móvil - debido a su flexibilidad para trabajar con recursos de bajo nivel: SMS, NFS, RFID, Internet, Cámara Fotográfica, fácil transportación, popularidad y costo). FORESTA contará con una API REST que permitirá la comunicación con FORESTA Móvil.

FORESTA Móvil podrá trabajar off-line ya que contará con una base de datos local para almacenar información propia del rol en uso y deberá sincronizarse automáticamente (al detectar conexión a internet) con FORESTA en un periodo máximo de 7 días.

Cada transportista y vehículo de transporte deberá estar registrado en el sistema, los vehículos contarán con un TAG RFID el cual podrá ser escaneado por el inspector para obtener información acerca del embarque. A futuro se plantea instalar lectores RFID en casetas de cobranza para conocer la ubicación y movimientos de los transportes autorizados.

FORESTA Móvil cumplirá con los requerimientos por rol de esta convocatoria y adicionará funcionalidades descritas mas adelante en esta presentación.

##Tecnología

**FORESTA** está desarrollado en MEAN Stack: aplicaciones web rápidas, robustas y mantenibles usando las tecnologías de MongoDB, Express, AngularJS y Node.js.

**FORESTA MÓVIL** está desarrollado con Android y hace uso de tecnologías: NFC y RFID, cuando **FORESTA MÓVIL** no tenga acceso a internet dará el alta de remisiones por medio de SMS.

Adicionalmente al dar de alta una remisión, esta se almacenará en un dispositivo NFC para poder ser revisada en cualquier momento por un inspector o en responsable del CAT cuando reciba la materia prima.

##Dependencias
**WEB**
- bcrypt ^0.8.0
- body-parser ~1.10.1
- connect-flash ^0.1.1
- connect-mongo ^0.5.3
- cookie-parser ~1.3.3
- debug ~2.1.1
- express ~4.10.6
- express-session ^1.10.0
- hbs ~2.8.0
- mongoose ^3.8.21
- morgan ~1.5.1
- passport ^0.2.1
- passport-local ^1.0.0
- node-uuid ~1.4.3
- object-to-xml 0.0.2"

##Instalación / Configuración 
Clonar el repositorio en su computadora, para ejecutar el proyecto de forma local ejecutar en la consola: DEBUG=retoforestal ./bin/www

Para publicar el sitio en Heroku:
- Crear una APP en heroku ($heroku create)
- Publicar hacia la APP ($git push heroku master)
- Agregar una base de datos a la aplicación de hoerku con mongo lab
- Abrir la aplicación en el navegador web ($heroku open)

Para ejecutar la APP móvil
- Importar el proyecto a Eclipse IDE
- Compilar y ejecutar

##Video (funcionamiento)
<a href="http://www.youtube.com/watch?feature=player_embedded&v=d3s9zIzvLmI" target="_blank"><img src="http://img.youtube.com/vi/d3s9zIzvLmI/0.jpg" alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>


##Screenshots
![alt tag](https://s3.amazonaws.com/foresta/foresta-centros-almacenamiento.jpg)
![alt tag](https://s3.amazonaws.com/foresta/foresta-materia-prima.jpg)
![alt tag](https://s3.amazonaws.com/foresta/foresta-remision-detalle.jpg)
![alt tag](https://s3.amazonaws.com/foresta/foresta-movil-login.jpg)
![alt tag](https://s3.amazonaws.com/foresta/foresta-movil-alta-transporte.jpg)

##Demo
- [http://foresta.herokuapp.com](http://foresta.herokuapp.com)

##¿Preguntas o problemas? 
Mantenemos la conversación del proyecto en nuestra página de problemas [issues] (https://github.com/NicotinaEstudio/foresta/issues). Si usted tiene cualquier otra pregunta, nos puede contactar por correo <soporte@nicotinaestudio.mx>.

##Contribuye
Para contribuir en el proyecto **FORESTA** haga click en el siguiente enlace ([Contribuir](#))

##Empresa

**Nuestra Misión**

> *Solucionar de forma creativa e innovadora problemas sociales y empresariales que sobrepasen sus expectativas y generen experiencias excepcionales. [Nicotina Estudio](http://www.nicotinaestudio.com)*

##Licencia

Copyright 2014 Nicotina Estudio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
