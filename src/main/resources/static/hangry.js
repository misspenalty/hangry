$('#search')
    .submit(
        function(event) {
            event.preventDefault();
            var query = $("#dish").val();

            $.getJSON("/hangry/restaurant?dish=" + query, function(json) {
                    var items = [];
                    $
                        .each(
                            json,
                            function(key,
                                val) {
                                items
                                    .push("<div class=\"row justify-content-center\">" +
                                        "<div class=\"card card-body bg-light\">" +
                                        "<a href=" + val['restaurant']['restaurantPage'] + ">" +
                                        val['restaurant']['name'] +
                                        "</a>" +
                                        val['name'] +
                                        "</div>" +
                                        "</div>");
                            });
                    $("#result").html(items.join(""));
                    $("#searchresults").html("<strong>" + items.length + "</strong> results found.");
                })
                .fail(function(jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status = 400) {
                        $("#result").html("");
                        $("#searchresults").html("<div class=\"row justify-content-center\">" +
                            JSON.parse(jqXHR.responseText)['message'] +
                            "</div>");
                    }
                })
        });