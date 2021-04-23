var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            description: $('#description').val(),
            circle: $('#circle').val(),
            pdfRef: $('#pdfRef').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/subjects',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('서브젝트가 등록되었습니다.');
            window.location.href = '/admin/subjects';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            name: $('#name').val(),
            description: $('#description').val(),
            circle: $('#circle').val(),
            pdfRef: $('#pdfRef').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/subjects/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('서브젝트가 수정되었습니다.');
            window.location.href = '/admin/subjects';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();