(deftemplate nodo
	(slot nombre)
	(slot tipo)
	(slot pregunta)
	(slot nodo-si)
	(slot nodo-no)
	(slot respuesta)
)


(deftemplate pregunta-respuesta
    (slot nombre)
	(slot tipo)
	(slot texto)
)

(defrule cambiar-nodo-si
	?n <- (nodo-actual ?nombre)
	?o <- (opcion si)
	(nodo (nombre ?nombre)(tipo decision)(nodo-si ?n-si))
=>
	(retract ?n)
	(retract ?o)
	(assert (nodo-actual ?n-si))
	(assert (estado pregunta))
)

(defrule cambiar-nodo-no
	?n <- (nodo-actual ?nombre)
	?o <- (opcion no)
	(nodo (nombre ?nombre)(tipo decision)(nodo-no ?n-no))
=>
	(retract ?n)
	(retract ?o)
	(assert (nodo-actual ?n-no))
	(assert (estado pregunta))
)

(defrule preguntar-responder
	?n <- (nodo-actual ?nombre)
	?e <- (estado pregunta)
	(nodo (nombre ?nombre)(tipo ?tipo)(pregunta ?pregunta)(respuesta ?respuesta))
	?p <- (pregunta-respuesta )
=>
	(retract ?e)
	(retract ?p)
	(if(eq ?tipo decision)then
		(assert (pregunta-respuesta (nombre ?nombre)(tipo ?tipo)(texto ?pregunta)))
	else
		(assert (pregunta-respuesta (nombre ?nombre)(tipo ?tipo)(texto ?respuesta)))
	)
)

(defrule reiniciar
	?n <- (nodo-actual ?nombre)
	?o <- (opcion ?opcion)
	(nodo (nombre ?nombre)(tipo respuesta))
	?p <- (pregunta-respuesta )
=>
	(retract ?n)
	(retract ?p)
	(if(eq ?opcion si)then
		(assert (nodo-actual raiz))
		(assert (estado pregunta))
		(assert (pregunta-respuesta))
	else
		(assert (pregunta-respuesta (tipo salir)))
	)
	(retract ?o)
)

(defrule comenzar
	(not (nodo-actual ?))
=>
	(load-facts "EE.dat")
	(assert (nodo-actual nodo1))
	(assert (estado pregunta))
	(assert (pregunta-respuesta))
)