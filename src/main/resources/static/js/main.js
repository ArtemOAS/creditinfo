$(document).ready( function () {
    var table = $('#employeesTable').DataTable({
        "sAjaxSource": "/sums",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "sumCredit" },
            { "mData": "periodCredit" },
            { "mData": "percentSum" },
        ]
    })
});
