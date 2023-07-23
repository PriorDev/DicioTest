Esta es mi prueba técnica para Dicio

La aplicación es capaz de conectarse con el api proporcionada y recuperar información así como
registrar nuevos usuarios. Por otra parte utiliza una base de datos local para almacenar l
a información que se obtiene del servidor remoto.

Esta aplicación fue generada utilizando Ktor, Dagger hilt, Room, Coil, Jetpack compose, flows,
view models, lifecycle, material design 3 y cameraX. Se implemento un patron de diseño MVVM,
se atendieron a las guias de desarrollo de CleanArchitecture así como princios SOLID y esta
dividida por features, con un core general, para optimizar el uso de la inyeccion de dependencias.

Tuve problemas para actualizar la información de los usuarios ya registrados así que esa
característica la descarte.

La ruta donde se guardan las fotografías es la siguiente
storage/emulated/0/Android/data/com.prior.diciotest/files/Pictures/Dicio/

El archivo apk esta en el directorio raiz de este repositorio