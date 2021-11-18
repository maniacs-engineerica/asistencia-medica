package com.tp3.asistenciamedica.entities

enum class TurnoEspecialidadEnum(val code: String) {
    OFTALMOLOGIA("Oftalmologia"),
    CARDIOLOGIA("Cardiologia"),
    CLINICA("Clinica"),
    Ginecologia("Ginecologia"),
    Hematologia("Hematologia"),
    KINESIOLOGIA("Kinesiologia");

    companion object {
        fun findByCode(code: String): TurnoEspecialidadEnum {
            lateinit var especialidad: TurnoEspecialidadEnum

            val values = TurnoEspecialidadEnum.values()

            values.forEach {
                if (it.code == code) {
                    especialidad = it
                }
            }


            return especialidad
        }
    }

}