[0.1.2.2]

- Se agrega comando y clase para el control de la regeneración natural
> `/regeneration` Obtener estado de la regeneración\
> `/regeneration change` Cambiar el estado de regeneración

- Se agrega comando para dar inicio al ciclo del UHC
> `/uhc` Información del plugin\
> `/uhc start` Iniciar ciclo del uhc

- Se eliminan las impresiones en consola cada vez que se colocaban o rompian bloques
- Se agrega clase para el uso de colores y formato de texto (subrayado, tachado, etc...)
- Se programa la scoreboard
> ![image](https://user-images.githubusercontent.com/29431799/201584588-6920d8f4-bea1-487a-81ab-ddcb0c13b01f.png)
- Se agrega comando para añadir jugadores a los equipos
>`/teams add_player <Team> <Jugador>`
- Se configura mecanica de revivido en base a estructura
> ![image](https://user-images.githubusercontent.com/29431799/201585579-3e97dfaf-6e20-4f55-a0c7-e28cdb2626e0.png)

[0.1.2.1]

- Se añade funcion para cuando alguien muere
> Al morir el jugador suelta una cabeza con el numero de identificacion unico del mismo

- Se configura barra de tiempo

- Se configura woldborder

> Los usuarios con op pueden hacer uso del comando

- Se añade comando timeBar para manejo de la barra de tiempo

- Al colocar una cabeza se guarda en la base de datos la información de la misma

- Al romper una cabeza se obtiene la información de la misma de la base de datos

- Se crean y programan metodos para la creación y borrado de los equipos

- Se crea detección de la estructura para revivir en una orientación
