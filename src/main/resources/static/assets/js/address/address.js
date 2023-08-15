const latitude = document.getElementById("latitudeJS").value;
const longitude = document.getElementById("longitudeJS").value;

const mapContainer = document.getElementById('map');
const mapOption = {
    center: new kakao.maps.LatLng(latitude, longitude),
    level: 4,
    // draggable: false
};
const map = new kakao.maps.Map(mapContainer, mapOption);
const geocoder = new kakao.maps.services.Geocoder();
const marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(latitude, longitude),
    map: map
});

kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            // 주소 정보를 가져와서 필요한 처리를 수행.
            const address = result[0].address.address_name;
            const latitude = mouseEvent.latLng.getLat();
            const longitude = mouseEvent.latLng.getLng();

            document.getElementById('addressInput').value = address;
            document.getElementById('latitudeInput').value = latitude;
            document.getElementById('longitudeInput').value = longitude;

            // 마커를 클릭한 위치에 표시.
            marker.setPosition(mouseEvent.latLng);
            marker.setMap(map);
        }
    });
});

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 선택한 주소 정보를 가져와서 필요한 처리를 수행.
            const address = data.address;
            document.getElementById("addressInput").value = address;

            // 주소로 좌표를 반환하여 지도에 표시.
            geocoder.addressSearch(address, function(results, status) {
                if (status === daum.maps.services.Status.OK) {
                    const result = results[0];
                    const lat = result.y;
                    const lng = result.x;

                    document.getElementById("latitudeInput").value = lat;
                    document.getElementById("longitudeInput").value = lng;

                    // 지도의 중심을 선택한 주소 위치로 이동.
                    const moveLatLon = new kakao.maps.LatLng(lat, lng);
                    map.setCenter(moveLatLon);
                    marker.setPosition(moveLatLon);
                    marker.setMap(map);
                }
            });
        }
    }).open();
}

function validateAddress() {

    const mainPlace = document.getElementById("addressInput").value;
    const latitude = document.getElementById("latitudeInput").value;
    const longitude = document.getElementById("longitudeInput").value;

    if (mainPlace.trim() === "" || latitude.trim() === "" || longitude.trim() === "") {
        // alert("입력되지 않은 값이 있습니다. 확인해주세요@.@");
        // SweetAlert2 팝업 띄우기
        Swal.fire({
            icon: 'error',
            title: '입력 오류',
            text: '입력되지 않은 값이 있습니다. 확인해주세요@.@'
        });
        return false;
    }
    // 주소록 개수 체크
    return checkAddressLimit();
}

function deleteAddress() {

    return checkedAddressMinLimit();
}

function checkAddressLimit() {
    // 주소록 테이블의 행 개수 확인
    const addressRowCount = document.querySelectorAll('#addressTable tr').length;

    // 저장 버튼 가져오기
    const saveButton = document.querySelector('#addressForm input[type="submit"]');

    // 주소록 행 개수가 10개 이상인 경우 에러 메시지 표시 및 저장 버튼 비활성화
    if (addressRowCount > 9) {
        const addressError = document.getElementById("addressError");
        alert("회원이 가지고 있는 주소가 10개가 넘었습니다. 주소 삭제 바랍니다.");
        saveButton.disabled = true;
        return false;
    }
    return true;
}

function  checkedAddressMinLimit() {

    // 주소록 테이블의 행 개수 확인
    const addressRowCount = document.querySelectorAll('#addressTable tr').length;

    // 저장 버튼 가져오기
    const deleteButton = document.querySelector('#deleteForm input[type="submit"]');

    if (addressRowCount < 2) {
        const addressError = document.getElementById("addressError");
        alert("주소는 최대 하나 이상은 가지고 있어야 합니다.");
        deleteButton.disabled = true;
        return false;
    }

    return true;
}


// 현재 위치 가져오기 버튼 생성
const currentLocationBtn = document.getElementById('currentLocationBtn');

currentLocationBtn.addEventListener('click', function() {
    getCurrentLocation();
});

function getCurrentLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            const lat = position.coords.latitude;
            const lng = position.coords.longitude;

            const locPosition = new kakao.maps.LatLng(lat, lng);

            // 현재 위치로 맵 중심 좌표 변경
            map.setCenter(locPosition);

            // 마커 위치 변경
            marker.setPosition(locPosition);
            marker.setMap(map);

            // 현재 위치를 주소로 변환하여 주소 입력 필드에 표시
            geocoder.coord2Address(lng, lat, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    document.getElementById("addressInput").value = result[0].address.address_name;
                }
            });

            // 현재 위치를 좌표로 입력 필드에 설정
            document.getElementById("latitudeInput").value = lat;
            document.getElementById("longitudeInput").value = lng;
        });
    } else {
        alert('위치 정보를 가져올 수 없습니다.');
    }
}
