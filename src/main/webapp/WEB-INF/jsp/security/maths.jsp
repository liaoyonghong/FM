<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- <base href="/"> -->
	<title></title>
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />

	<link rel="stylesheet" href="bower_components/kendo-ui/styles/kendo.common-bootstrap.min.css">
	<link rel="stylesheet" href="bower_components/kendo-ui/styles/kendo.rtl.min.css">
	<link rel="stylesheet" href="bower_components/kendo-ui/styles/kendo.bootstrap.min.css">
	<link rel="stylesheet" href="bower_components/kendo-ui/styles/kendo.bootstrap.mobile.min.css">
	<link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.css">
	<link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap-table.min.css">
    <link rel="stylesheet" type="text/css" href="bower_components/calc/css/calculate.css"/>
    <style type="text/css">
.k-button {
    background-color: green!important;
    color: #fff!important;
}
.k-button:hover {
    background-color: #8eb15a!important;
}
.remarkContent {
    box-shadow: rgba(0, 0, 0, 0.08) 0px 1px 2px 1px, rgba(0, 0, 0, 0.08) 0px 3px 6px;
    padding: 9px;
    border-width: 5px;
    border-style: solid;
    border-color: rgb(240, 240, 240);
    border-image: initial;
}
.vs-table {
  width: 100%;
  border: solid lightgray;
  border-width: 1px 0 0 1px;
  box-shadow: 0 1px 2px 1px rgba(0, 0, 0, 0.08), 0 3px 6px rgba(0, 0, 0, 0.08);
  margin-bottom: 1px;
}

.vs-table th,
.vs-table td{
  border: solid lightgray;
  border-width: 0 1px 1px 0;
  padding: 1px 5px;
}
.calc_title {
  width: 150px;
  color: #00f;
  font-size: 18px;
  font-weight: bold;
}

.calc_body .calc_body_value {
  border: 1px solid #ccc;
  border-radius: 3px;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  background: #fbfbfb;
  margin: 0;
  width: 60px;
  height: 25px;
  font-weight: normal;
  text-align: center;
  vertical-align: middle;
}

.calc_to_value {
  border: 0;
  color: blue;
  margin-left: 50px;
}
.calc_to_value::before {
  content: "to";
  color: black;
  font-weight: normal;
  margin-left: -70px;
  padding-right: 10px;
}

.math_BMI_Body .calc_body_label {
  text-align: right;
  width: 20px !important;
}
.math_BMI_Body .calc_body_value > span {
  color: blue;
}

.calc_body .calc_body_label {
  margin: 0 10px 0 5px;
  width: 80px;
  font-weight: normal;
}

.calc_body_calc:hover {
  background-color: #aeaeae;
}

.calc_body_conv label.calc_to_value > span[name],
.calc_body_date label.calc_to_value > span[name],
.calc_gestation_body label.calc_to_value > span[name] {
  border: 1px solid #ccc;
  border-radius: 3px;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  background: #fbfbfb;
  width: 60px;
  height: 25px;
  line-height: 25px;
  display: inline-block;
  text-align: center;
  vertical-align: middle;
}
.calc_body_conv .k-widget.k-numerictextbox,
.calc_body_date .k-widget.k-numerictextbox,
.calc_gestation_body .k-widget.k-numerictextbox {
  width: 120px;
}
.calc_body_conv .k-numeric-wrap > .k-select,
.calc_body_date .k-numeric-wrap > .k-select,
.calc_gestation_body .k-numeric-wrap > .k-select {
  display: none;
}
.calc_body_conv .k-numeric-wrap,
.calc_body_date .k-numeric-wrap,
.calc_gestation_body .k-numeric-wrap {
  padding-right: 0;
}

.mathDOBList tr td {
  text-align: center;
}
.mathDOBList tr td:first-child {
  font-weight: bold;
  line-height: 28px;
}

.mathGestationList tr td {
  text-align: center;
}
.mathGestationList tr td:first-child {
  font-weight: bold;
  line-height: 25px;
}
    </style>

	<script src="bower_components/jquery/dist/jquery.js"></script>
	<script src="bower_components/angular/angular.js"></script>
	<script src="bower_components/angular-resource/angular-resource.js"></script>
	<script src="bower_components/angular-cookies/angular-cookies.js"></script>
	<script src="bower_components/angular-sanitize/angular-sanitize.js"></script>
	<script src="bower_components/angular-bootstrap/ui-bootstrap.js"></script>
	<script src="bower_components/angular-translate/angular-translate.min.js"></script>
	<script src="bower_components/calc/js/calc.js"></script>
    <script src="bower_components/moment/min/moment.min.js"></script>
	<script src="assets/js/kendo.all.min.js"></script>
	<script>
	angular.module('angulardemoApp', ['ngResource', 'pascalprecht.translate', 'ui.bootstrap', 'kendo.directives'])
		.config(['$translateProvider', function($translateProvider) {
			$translateProvider.useStaticFilesLoader({
				prefix: 'app/i18n/locale.',
				suffix: '.json'
            });
		}]);

	</script>
	<script src="assets/js/maths.js"></script>
</head>

<body ng-app="angulardemoApp" ng-controller="MathsCtrl">
	<div id="appmenu"></div>
	<div id="mathsContent">
		<div style="display: none;" id="mathsConvBmiContainer" ng-include="'components/windows/mathConvBmi.html'" onload="finishLoading('ConvBmi')"></div>
		<div style="display: none;" id="mathsDOBContainer" ng-include="'components/windows/mathDOB.html'" onload="finishLoading('DOB')"></div>
		<div style="display: none;" id="mathsGestationContainer" ng-include="'components/windows/mathGestation.html'" onload="finishLoading('Gestation')"></div>
	</div>
</body>
<script>
angular.module('angulardemoApp').controller('MathsCtrl', function($rootScope, $scope, $resource, $timeout, $compile, MathDOBService, MathGestationService, MathConvBmiService) {
    $rootScope.serviceRoot = "/THCMSApplication";
    $scope.finishLoading = function(e) {
        setTimeout(() => $scope.finishLoadingFn(e), 300);
        setTimeout(() => $scope.finishLoadingFn(e), 700);
    }
    $resource($rootScope.serviceRoot + '/parameters/readSystemParas').query(function(response) {
        $rootScope.appParameters = response[0];
        $timeout(() => {
            var menu = $("#appmenu").kendoMenu({
                dataSource: [{
                    text: "Conversion/BMI"
                }, {
                    text: "DOB"
                }, {
                    text: "Gestation"
                }],
                select() {
                    // $("#mathsContentDisplay").html($compile($("#mathsContent>div").eq($(event.target.parentElement).index()))($scope));
                    $("#mathsContent>div").hide().eq($(event.target.parentElement).index()).show();
                }
            });
            $scope.finishLoadingFn = function(e) {
                $("#mathsContent>div").hide().eq(Number(window.location.search.substring(1))).show();
                $timeout(() => {
                    if (e === 'ConvBmi') {
                        return MathConvBmiService($scope, '#mathsConvBmiContainer');
                    }
                    if (e === 'DOB') {
                        return MathDOBService($scope, '#mathsDOBContainer');
                    }
                    if (e === 'Gestation') {
                        return MathGestationService($scope, '#mathsGestationContainer');
                    }
                });
            }
        });
    });
})

</script>

</html>
