DOSSEG
.MODEL SMALL
.STACK 100h
.DATA
	HW db 48h,65h,6Ch,6Ch,6Fh,20h,57h,6Fh,72h,6Ch,64h,24h
.CODE
	BEGIN:
		MOV AX, @data
		MOV DS, AX
		MOV ES, AX
		
		LEA DX, HW
		MOV AH, 09h
		INT 21h
		
		MOV AH, 4Ch
		INT 21h
	END BEGIN
