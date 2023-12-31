<h1 align="center">
 Core Automatización Infinite Growth
</h1>

## Objetivo

Este Core de Automatización esta realizado con el objetivo cumplir con el reto de Infinite Growth.


## Arquitectura de Core

Este entregable para el reto de Infinite Growth tiene como solución un framework de tipo data driven construído con Java, Selenium, RestAssured, TestNG que utiliza como patrón de diseño 
 Page Object Model y Service Model para la automatización de pruebas Web y Servicios.

  El beneficio de tener un repositorio core es que se pueden generar nuevos repositorios que extiendan de él  plantearlo de esta manera es que se pueden realizar mantenimientos al core y se pueden crear proyectos independientes que extiendan de las dependencias del core, así las actualizaciones y mantenimientos quedan centralizados. Para actualizar el core en un proyecto que lo implemente se tendría que modificar la versión de unica depedencia necesaria en el archivo pom.xml:
  ```sh
    <dependencies>
        <dependency>
            <groupId>io.github.otordzdl</groupId>
            <artifactId>infinite-growth-automation-core</artifactId>
            <version>4.0</version>
        </dependency>
    </dependencies>
  ```

El core tiene la siguiente estructura:

![image](https://github.com/otordzdl/infinite-growth-automation-framework-project/assets/27450684/7464ff74-004a-4953-8417-b6166da9b284)

Donde:

- Se plantea una serie de Interfaces las cuales permiten generar las bases BaseWebTest y BaseServiceTest de las cuales extenderan aquellas clases que se utilicen para crear Test de Web y Servicios
- Un clase Listener que estará revisando ciertas condiciones de Estatus de ejecución para el reporteo, el reporteo se implementa con el ExtentManager que utiliza el famoso ExtentReport
- Para las pruebas de Navegadores:
  - Se tiene un wrapper para encapsular acciones especificas de selenium para un mejor mantenimiento
  - Una fábrica de WebDriverManager para gestionar las configuraciones necesarias de los drivers ya sea por tipo(Chrome,Firefox,Edge y Safari) y Sistema operativo
  - Un Manager de Remote Web Driver para la conexión a device farm, hay diferentes configuraciones dependiendo el proveedor del servicio de browser cloud

## Automatizador
⭐️ **Otoniel Rodríguez Delgado**⭐️ 
