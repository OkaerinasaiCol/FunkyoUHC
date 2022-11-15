# **[0.1.2.3]**

- Se agrega comandos para hacer chat global cuando ya haya iniciado el UHC
> `/global` [alias] `/g` `/G` `/GLOBAL` *\<args\>* Menssaje a mandar chat global
> - En caso de que se mande el mensaje sin el comando el mensaje se manda únicamente al equipo
> 
> ![imgage](https://user-images.githubusercontent.com/29431799/201827943-74313e95-923b-471c-8b3e-bbfec3a496df.jpg)

- Se configura para que los ghast no dropeen la lagrima de ghast y ahora esta debe de ser crafteada con el siguiente 
crafteo:
>![image](https://user-images.githubusercontent.com/29431799/201828833-2e89e7e3-dedb-4a56-9fdf-18bbc1bf40f7.jpg)

- Se configura crafteo de manzana encantada con cabeza de jugador
> ![image](https://user-images.githubusercontent.com/29431799/201829045-53045870-e2d9-4651-919e-2e26fdf0b9ca.jpg)

- Se configura crafteo y funcionamiento de las brújulas para buscar enemigos
> ![image](https://user-images.githubusercontent.com/29431799/201829358-95640ec4-940f-4493-b4c0-751f3d234e7d.jpg)
> - Estas siempre apuntan al jugador **Enemigo** más cercano; funciona en ambas manos,
> en caso de que se cambie por brújulas normales apuntarán a 0 0
> 
> ![image](https://user-images.githubusercontent.com/29431799/201830068-5202eae9-0d76-4fcd-be67-4c6680286377.jpg)
> ![image](https://user-images.githubusercontent.com/29431799/201830157-2993e722-0718-4106-86b8-e478855f8444.jpg)

# **[0.1.2.2]**

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
- Se configura mecanica de revivido basándonos en estructura
> ![image](https://user-images.githubusercontent.com/29431799/201585579-3e97dfaf-6e20-4f55-a0c7-e28cdb2626e0.png)

# **[0.1.2.1]**

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
