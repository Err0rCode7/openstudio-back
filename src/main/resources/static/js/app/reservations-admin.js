var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-delete-row').on('click', function () {
            _this.deleteRow($(this));
        });

    },
    save : function () {
        var data = {
            subjectName: $('#subjectName').val(),
            userName: $('#userName').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/reservations',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('예약이 등록되었습니다.');
            window.location.href = '/admin/reservations';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    deleteRow : function(btn) {
        var tr = btn.parent().parent();
        var td = tr.children();
        var id = td.eq(0).text();
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/reservations/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('예약이 취소되었습니다.');
            window.location.href = '/admin/reservations';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();