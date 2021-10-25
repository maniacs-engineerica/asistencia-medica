package com.tp3.asistenciamedica.entities

enum class TurnoStatusEnum {
    DISPONIBLE {
        override fun nextStatus() = RESERVADO
    },
    RESERVADO {
        override fun nextStatus() = CERRADO
    },
    CERRADO {
        override fun nextStatus() = CERRADO
    };

    abstract fun nextStatus(): TurnoStatusEnum
}

