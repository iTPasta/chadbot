str = '''n_default (0) - 0 % 	4 899 979 n_term (1) - 25.336 % 	328 144 n_form (2) - 1.697 %
241 n_pos (4) - 0.001 % 	1 001 n_concept (5) - 0.005 % 	124 n_flpot (6) - 0.001 %
116 274 n_chunk (8) - 0.601 % 	4 779 n_question (9) - 0.025 % 	9 919 066 n_relation (10) - 51.289 %
2 754 n_rule (11) - 0.014 % 	1 990 n_analogy (12) - 0.01 % 	1 n_commands (13) - 0 % 	9 n_synt_function (14) - 0 %
314 n_relation_name (15) - 0.002 % 	78 n_data (18) - 0 % 	110 n_data_pot (36) - 0.001 % 	539 448 n_context (200) - 2.789 %
43 n_pos_seq (222) - 0 % 	2 562 765 n_link (444) - 13.251 % 	1 856 n_AKI (666) - 0.01 % 	960 692 n_wikipedia (777) - 4.967 %
2 n_group (1002)'''

result = ""
word = ""
number = ""

i = 0
while i < len(str):
    if str[i] == " ":
        i += 1
        continue
    if str[i] == "(":
        i += 1
        while str[i] != ")":
            number += str[i]
            i += 1
        result += word.upper().replace("-", "_").replace(">", "_TO_").replace("/", "_") + "(" + number + ',"' + word + '"),\n'
        word = ""
        number = ""
        while i < len(str) and str[i] != "n":
            i += 1
        continue
    word += str[i]
    i += 1

print(result)