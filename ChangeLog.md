# **[0.1.2.3]**

- Se agregan comandos para remover jugadores, renombrar equipos, cambiar color y eliminar equipos (verificar error #8 )

- Se estiliza el prompt en consola del inicio del plugin

> ![image](https://user-images.githubusercontent.com/29431799/202078947-5f3cc003-95f7-46f3-a10d-6cc9909262a4.png)

- Se configura WorldBorder para que se cambie de un tamaño a otro en una cantidad de tiempo específico

> ![image](https://user-images.githubusercontent.com/29431799/202079162-1d8a0fc0-cf4c-4480-98f7-c690090a92d5.jpg)
> ![image](https://user-images.githubusercontent.com/29431799/202079226-9085ee50-fbcf-4d89-9b62-90f7532a785c.jpg)

- Se elimina comando para activar la barra de tiempo restante
- Se configura barra de tiempo restante de forma automatica al inicar el UHC, a su vez que se programa rondas y
  tiempo restante

> ![image](https://user-images.githubusercontent.com/29431799/202079614-7f8538e9-2af1-4da4-87df-ab487c8fd51e.jpg)

- Se configuran títulos para Informar sobre cambios de ronda

> ![image](https://user-images.githubusercontent.com/29431799/202079799-f20dc50f-e170-41ac-a117-22b2df950cd1.jpg)

- Se configura avisos de tiempo restante para la reducción del tamaño del borde del mapa

> ![image](https://user-images.githubusercontent.com/29431799/202080060-c4f2c089-82d9-4c2c-a0be-a400a8ccdbf8.jpg)

- Se configura para que no se regenere vida con saturación y que la saturación no se reduzca en caso de faltar vida
  (Se sigue poniendo regenerar vida con pociones de regeneración, manzanas doradas, etc...)

> ![image](https://user-images.githubusercontent.com/29431799/202080381-e9f98451-4961-41ce-add8-35c7864e254d.jpg)

# **[0.1.2.2]**

- Se agrega comandos para hacer chat global cuando ya haya iniciado el UHC

> `/global` [alias] `/g` `/G` `/GLOBAL` *\<args\>* Menssaje a mandar chat global
> - En caso de que se mande el mensaje sin el comando el mensaje se manda únicamente al equipo
>
> ![imgage](https://user-images.githubusercontent.com/29431799/201827943-74313e95-923b-471c-8b3e-bbfec3a496df.jpg)

- Se configura para que los ghast no dropeen la lagrima de ghast y ahora esta debe de ser crafteada con el siguiente
  crafteo:

> ![image](https://user-images.githubusercontent.com/29431799/201828833-2e89e7e3-dedb-4a56-9fdf-18bbc1bf40f7.jpg)

- Se configura crafteo de manzana encantada con cabeza de jugador

> ![image](https://user-images.githubusercontent.com/29431799/201829045-53045870-e2d9-4651-919e-2e26fdf0b9ca.jpg)

- Se configura crafteo y funcionamiento de las brújulas para buscar enemigos

> ![image](https://user-images.githubusercontent.com/29431799/201829358-95640ec4-940f-4493-b4c0-751f3d234e7d.jpg)
> - Estas siempre apuntan al jugador **Enemigo** más cercano; funciona en ambas manos, en caso de que se cambie por
    brújulas normales apuntarán a 0 0
>
> ![image](https://user-images.githubusercontent.com/29431799/201830068-5202eae9-0d76-4fcd-be67-4c6680286377.jpg)
> ![image](https://user-images.githubusercontent.com/29431799/201830157-2993e722-0718-4106-86b8-e478855f8444.jpg)

# **[0.1.2.1]**

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

> `/teams add_player <Team> <Jugador>`

- Se configura mecanica de revivido basándonos en estructura

> ![image](https://user-images.githubusercontent.com/29431799/201585579-3e97dfaf-6e20-4f55-a0c7-e28cdb2626e0.png)

# **[0.1.2]**

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
