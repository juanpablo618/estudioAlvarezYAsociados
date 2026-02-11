function javascriptFunction() {
        up.jq.click();
    }

function handleSubmit(args, dialog) {
       
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function handleSubmitDos(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
              
    }
}

 // toma el valor del growl y lo muestra con un diseño mas moderno (sweet-alert)
 // si se quiere volver al diseño de primefaces , solo comentar esta funcion 
//ESTE ESPERA QUE TERMINE UN MENSAJE PARA MOSTRARTE RECIEN EL OTRO


PrimeFaces.widget.Growl.prototype.show = function(messages) {
    if (!messages || messages.length === 0) return;

    console.log("Mensajes recibidos en growl:", messages);

    let index = 0;

    const mostrarSiguiente = () => {
        if (index >= messages.length) return;

        const msg = messages[index];
        const text = msg.detail || msg.summary || "";
        const title = msg.summary || "";

        // Iconos según severidad / contenido
        let icon = "info";
        if ((msg.severity === "success") || text.toLowerCase().includes("exitosamente") || title.toLowerCase().includes("ok")) icon = "success";
        else if (msg.severity === "warn") icon = "warning";
        else if (msg.severity === "error" || msg.severity === "fatal" || title.toLowerCase().includes("error")) icon = "error";

        // Condición de mensajes bloqueantes
        const esBloqueante =
            icon === "error" ||
            text.toLowerCase().includes("te quedaron");

        // Configuración de SweetAlert2
        Swal.fire({
            toast: true,
            position: 'top-end',
            icon: icon,
            title: title,
            text: text,
            showConfirmButton: false,
            showCloseButton: true,           // siempre muestra la X
            allowOutsideClick: !esBloqueante,
            allowEscapeKey: true,
            timer: esBloqueante ? undefined : 6000, // bloqueantes sin timer
            timerProgressBar: !esBloqueante          // bloqueantes sin barra
        }).then(() => {
            index++;
            mostrarSiguiente(); // siguiente mensaje
        });
    };

    mostrarSiguiente(); // inicia con el primero
};


// Para que los calendarios aparescan en español , solo funciona si en el calendar pones la propiedad locale="es" y poniendo en el head la ruta de este js
PrimeFaces.locales['es'] = {
        closeText: 'Cerrar',
        prevText: 'Anterior',
        nextText: 'Siguiente',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
        dayNamesMin: ['D', 'L', 'M', 'X', 'J', 'V', 'S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Sólo hora',
        timeText: 'Tiempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Fecha actual',
        ampm: false,
        month: 'Mes',
        week: 'Semana',
        day: 'Día',
        allDayText: 'Todo el día'
    };

















