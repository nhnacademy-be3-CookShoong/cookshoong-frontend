const geocoder = new daum.maps.services.Geocoder();

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById("mainPlace").value = data.address;
            geocoder.addressSearch(data.address, function (results, status) {
                if (status === daum.maps.services.Status.OK) {
                    const result = results[0];
                    const lat = result.y;
                    const lng = result.x;
                    document.getElementById("latitude").value = lat;
                    document.getElementById("longitude").value = lng;
                    document.getElementById("mainPlace").style.borderColor = '#d3f2d4';
                }
            });
        }
    }).open()
}
