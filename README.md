# Microservicio REST

**Objetivo**

Ofrecer una nueva función a nuestros clientes mostrando productos similares al que están viendo actualmente.

**Ejecutar Servicio**

* Set profile Dev: `-Dspring.profiles.active=dev`

* `mvn clean install`

* `mvn spring-boot:run`

**Estructura**

| Package     | Descripcion                                                  |
|-------------|--------------------------------------------------------------|
| commons/    | Clases comunes al proyecto                                   |
| config/     | Configuraciones                                              |
| constants/  | Clases con utilidades o auxiliares                           |
| controller/ | Controladores                                                |
| model/      | Capa de modelo de clases                                     |
| service/    | Capa de servicios                                            |

---

### Estructura

**config**

* Contiene clases de configuraciones necesarias

**model**

Modelo de clases de cada objeto

**constants**

Contiene constantes, metodos estaticos que pueden ser reutilizados en el proyecto como asi tambien una clase de logueo
estandar pre-configurada.

### Informacion adicional

**Resilience4j**

La clase donde se utiliza las configuraciones de la libreria resilience4j.

Ruta de la clase: `commons/resilience4j/Resilience4jService`

Ejemplo:

```
        resilience4jService.executeProducts(() -> productService.getSimilarProducts(productId))
```

Donde:
> * resilience4jService.executeCellulars: Metodo generado para asignar una configuración personalizada de la libreria resilience4j.
> * cellularsService.getCellularPlans: Metodo a encapsular con el fin de ejecutar las configuraciones realizadas en la libreria resilience4j.
> * request: Parámetros de entrada del metodo.

* **Circuit Breaker**

Para utilizar esta propiedad se debe asignar la anotacion `@CircuitBreaker` indicando tambien el nombre de la
configuracion en el metodo que corresponda encapsular.

Ejemplo:

```
    @CircuitBreaker(name = PRODUCTS_API)
    public <T> T executeProducts(Supplier<T> operation) {
        return operation.get();
    }
```

* **Rate Limiter**

Para utilizar esta propiedad se debe asignar la anotacion `@RateLimiter` indicando tambien el nombre de la configuracion
en el metodo que corresponda encapsular.

Ejemplo:

```
    @RateLimiter(name = PRODUCTS_API)
    public <T> T executeProducts(Supplier<T> operation) {
        return operation.get();
    }
```

* **Bulkhead**

Para utilizar esta propiedad se debe asignar la anotacion `@Bulkhead` indicando tambien el nombre de la configuracion en
el metodo que corresponda encapsular.

Ejemplo:

```
    @Bulkhead(name = PRODUCTS_API)
    public <T> T executeProducts(Supplier<T> operation) {
        return operation.get();
    }
```

* **Retry**

Para utilizar esta propiedad se debe asignar la anotacion `@Retry` indicando tambien el nombre de la configuracion en el
metodo que corresponda encapsular.

Ejemplo:

```
    @Retry(name = PRODUCTS_API)
    public <T> T executeProducts(Supplier<T> operation) {
        return operation.get();
    }
```

**Excepciones y Log**

Todas las excepciones a traves del logueo por aspecto.

Ruta de la clase: `commons/aop/LogAspect`

Ademas las excepciones deben generarse utilizando las siguientes clases `BusinessException`, una personalizada 
como `CustomException` generada para este servicio con un código de error y un mensaje que informe el error. En el caso que sea necesario tambien se puede
ingresar la excepcion capturada. De esta forma la clase LogAspect podra capturar el error y que el mismo se registre en
el servicio.

* **Error**

El servicio tiene una estructura generica de respuesta para los casos de error, la misma esta compuesta de la siguiente
informacion:

* `resultCode`: Registra el codigo de error/ejecucion.
* `resultMessage`: Registra el mensaje de error/ejecucion.

Ejemplo:

````
{
    "resultCode": "100001",
    "resultMessage": "Error al obtener detalles del producto: %s"
}
````

* **Estructura de Logueo**

La estructura de logueo se define en la clase `LogAspect` en el constructor de la clase.


Dando asi por ejemplo esta estructura final de logueo:

`[TRANSACTION_ID=XX | SESSION_ID=XX | SERVICE_ID=XX | CHANNEL_ID=XX | OPERATION=XX | CODE=XX | DESCRIPTION=XX | ELAPSED=XX | REQUEST=XX | RESPONSE=XX]`
