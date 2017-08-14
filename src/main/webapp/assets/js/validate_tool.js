var validate_tool = {
		isNotNull : 
			function (str){
				if(str != 'undefined' && str.trim() != "")
					return true; 
				else
					return false;
			},
		isNumber :
			function(number){
			
				if (isNaN(number))
					return false;
				else
					return true;
			},
		isPhone : 
			function(phone){
			   
				if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(phone)))
			    	return false; 
			    else
			    	return true;
			},
		isDouble : 
			function(decimals){
			alert("123")
			if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(decimals)))
				return false; 
			else
				return true;
		}
}